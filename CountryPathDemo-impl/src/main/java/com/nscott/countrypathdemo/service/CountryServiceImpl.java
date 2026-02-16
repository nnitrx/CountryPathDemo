package com.nscott.countrypathdemo.service;

import com.nscott.countrypathdemo.constant.ErrorConstants;
import com.nscott.countrypathdemo.model.CountryEntity;
import com.nscott.countrypathdemo.model.CountryNode;
import com.nscott.countrypathdemo.repository.CountryRepository;
import com.nscott.countrypathdemo.util.CountryUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public List<String> getPathBetweenCountries(String startId, String destId) {

        //Return own country if points to itself
        if (startId.equals(destId)) {
            return List.of(startId);
        }

        CountryEntity start = countryRepository.findById(startId.toUpperCase())
                .orElseThrow(() -> new NoSuchElementException(String.format(ErrorConstants.INVALID_COUNTRY, startId)));
        CountryEntity dest = countryRepository.findById(destId.toUpperCase())
                .orElseThrow(() -> new NoSuchElementException(String.format(ErrorConstants.INVALID_COUNTRY, destId)));

        //Trivial checks before route generation
        if(!CountryUtils.isTraversable(start, dest)) {
            throw new IllegalArgumentException(ErrorConstants.NO_POSSIBLE_PATH);
        }

        CountryNode startNode = new CountryNode(start, 0D, CountryUtils.calculateHaversineDistance(start, dest));

        Map<String, CountryNode> nodePathMap = new HashMap<>();
        nodePathMap.put(start.getId(), startNode);

        //Sort each new neighboring node by shortest distance to destination first
        SortedSet<CountryNode> nodesToVisit = new TreeSet<>(Comparator.comparing(CountryNode::getDestScore));
        nodesToVisit.add(startNode);

        //Iterate each branching neighbor by ascending order from shortest destination distance
        while (!nodesToVisit.isEmpty()) {
            CountryNode currCountry = nodesToVisit.removeFirst();
            if (currCountry.getCountry().getId().equals(destId)) {
                //Destination found: construct and return path
                return constructFinalPath(currCountry);
            }

            searchBorderCountries(currCountry, dest, nodesToVisit, nodePathMap);
        }

        //All possible nodes exhausted with no destination found
        throw new IllegalArgumentException(ErrorConstants.NO_POSSIBLE_PATH);
    }

    /**
     * Fetch each of the bordering countries, calculating the next node closest to destination to traverse
     *
     * @param currCountry Currently selected country in path
     * @param destCountry Destination country
     * @param nodesToVisit Sorted nodes to traverse next
     * @param nodePathMap Country id to node map
     */
    private void searchBorderCountries(CountryNode currCountry, CountryEntity destCountry, SortedSet<CountryNode> nodesToVisit, Map<String, CountryNode> nodePathMap) {
        countryRepository.findAllById(currCountry.getCountry().getBorders()).stream()
            .map(c -> {
                CountryNode cNode = nodePathMap.getOrDefault(c.getId(), new CountryNode(c));
                nodePathMap.put(c.getId(), cNode);
                return cNode;
            }).forEach(cNode -> {
                double borderDist = currCountry.getBorderScore() + CountryUtils.calculateHaversineDistance(currCountry.getCountry(), cNode.getCountry());
                if (borderDist < cNode.getBorderScore()) {
                    cNode.setPrevNode(currCountry);
                    cNode.setBorderScore(borderDist);
                    cNode.setDestScore(borderDist + CountryUtils.calculateHaversineDistance(cNode.getCountry(), destCountry));
                    nodesToVisit.add(cNode);
                }
            });
    }

    private List<String> constructFinalPath(CountryNode destNode) {
        List<String> countryPath = new LinkedList<>();

        CountryNode currNode = destNode;

        while(currNode != null) {
            countryPath.addFirst(currNode.getCountry().getId());
            currNode = currNode.getPrevNode();
        }

        return countryPath;
    }
}
