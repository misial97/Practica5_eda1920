package material.usecase;

import material.Position;
import material.tree.binarysearchtree.AVLTree;
import material.tree.binarysearchtree.BinarySearchTree;
import material.tree.binarysearchtree.LinkedBinarySearchTree;
import material.tree.binarysearchtree.RBTree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Practica 5.
 * Realizada por: Miguel Sierra Alonso
 */

public class FlightQuery {
    /**
     * En cada caso solo se ordena por las claves especificadas.
     *
     * Al buscar por (date1, date1) se deberia devolver Vuelo2 pero al ser la misma fecha se compara
     * por codigo de vuelo y se excluye.
     *
     * Vuelo( date1, code=550) > Vuelo2 (date1, code=0)
     */

    private BinarySearchTree<Flight> bstByDates;
    private BinarySearchTree<Flight> bstByDestinations;
    private BinarySearchTree<Flight> bstByCompanyAndCode;

    private Comparator<Flight> compareByDate;
    private Comparator<Flight> compareByDestination;
    private Comparator<Flight> compareByCompanyAndCode;

    public FlightQuery(){

        this.initializeComparators();

        //Funciona con los tres tipos de BST

        this.bstByDates = new LinkedBinarySearchTree<>(this.compareByDate);
        this.bstByDestinations = new LinkedBinarySearchTree<>(this.compareByDestination);
        this.bstByCompanyAndCode = new LinkedBinarySearchTree<>(this.compareByCompanyAndCode);
    /*
        this.bstByDates = new AVLTree<>(this.compareByDate);
        this.bstByDestinations = new AVLTree<>(this.compareByDestination);
        this.bstByCompanyAndCode = new AVLTree<>(this.compareByCompanyAndCode);

        this.bstByDates = new RBTree<>(this.compareByDate);
        this.bstByDestinations = new RBTree<>(this.compareByDestination);
        this.bstByCompanyAndCode = new RBTree<>(this.compareByCompanyAndCode);
     */
    }

    private void initializeComparators(){
        this.compareByDate = new Comparator<Flight>() {
            @Override
            public int compare(Flight o1, Flight o2) {
                int diffYear = o1.getYear() - o2.getYear();
                int diffMonth = o1.getMonth() - o2.getMonth();
                int diffDay = o1.getDay() - o2.getDay();
                if(diffYear != 0)
                    return diffYear;
                if(diffMonth != 0)
                    return diffMonth;
                if(diffDay != 0)
                    return diffDay;
                return 0;
            }
        };
        this.compareByDestination = new Comparator<Flight>() {
            @Override
            public int compare(Flight o1, Flight o2) {
                return o1.getDestination().compareTo(o2.getDestination());
            }
        };
        this.compareByCompanyAndCode = new Comparator<Flight>() {
            @Override
            public int compare(Flight o1, Flight o2) {
                int compareCompany = o1.getCompany().compareTo(o2.getCompany());
                int compareFlightCode= o1.getFlightCode() - o2.getFlightCode();

                if(compareCompany != 0)
                    return compareCompany;

                return compareFlightCode;
            }
        };
    }

    public void addFlight(Flight flight) {
        if(flight != null){
            this.bstByDates.insert(flight);
            this.bstByDestinations.insert(flight);
            this.bstByCompanyAndCode.insert(flight);
        }
    }

    public Iterable<Flight> searchByDates(int start_year, int start_month, int start_day, int end_year, int end_month, int end_day) throws RuntimeException {
        Flight start = new Flight();
        start.setDate(start_year, start_month, start_day);

        Flight end = new Flight();
        end.setDate(end_year, end_month, end_day);

        return this.query(start, end, this.compareByDate, this.bstByDates);
    }

    public Iterable<Flight> searchByDestinations(String start_destination, String end_destination) throws RuntimeException {
        Flight start = new Flight();
        start.setDestination(start_destination);

        Flight end = new Flight();
        end.setDestination(end_destination);

        return this.query(start, end, this.compareByDestination, this.bstByDestinations);
    }


    public Iterable<Flight> searchByCompanyAndFLightCode(String start_company, int start_flightCode, String end_company, int end_flightCode) {
        Flight start = new Flight();
        start.setCompany(start_company);
        start.setFlightCode(start_flightCode);

        Flight end = new Flight();
        end.setCompany(end_company);
        end.setFlightCode(end_flightCode);

        return this.query(start, end, this.compareByCompanyAndCode, this.bstByCompanyAndCode);
    }


    //Logica de la query simplificada en un unico metodo
    private Iterable<Flight> query(Flight start, Flight end, Comparator<Flight> comparator, BinarySearchTree<Flight> bst){
        if(comparator.compare(start, end) > 0)
            throw new RuntimeException("Invalid range. (min>max)");

        List<Flight> resultQuery = new ArrayList<>();
        Iterator<Position<Flight>> it = bst.iterator();

        while (it.hasNext()){
            Position<Flight> actualNode = it.next();
            if((comparator.compare(actualNode.getElement(), start) >= 0) &&
                    (comparator.compare(actualNode.getElement(), end) <= 0))
                resultQuery.add(actualNode.getElement());
        }
        return resultQuery;

    }
}