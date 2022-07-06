package Appelli.Appello_11_11_2020.Esercizio_WS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AziendaService {


    private HashMap<Integer, ArrayList<IncassoProdotto>> databse = new HashMap<>();

    /*nella descrizione del punto 1 non è specificato
    bisogna passare anche un importo nel metodo
    lo si puo vedere in VenditaRequest nel file WSDL che espone tutti i
    parametri da passare, in VenditaResponse invece non è specificato niente quindi
    è void il risultato
     */
    public void Vendita(int id, String nome, Double importo){
        boolean trovato = false;
        for(Map.Entry<Integer, ArrayList<IncassoProdotto>> entry: databse.entrySet()){
           if(entry.getKey() == id){
               trovato = true;
               ArrayList<IncassoProdotto> listaVini = entry.getValue();
               listaVini.add(new IncassoProdotto(nome, ""+importo));
           }
       }
       if(!trovato){
           ArrayList<IncassoProdotto> nuovaLista = new ArrayList<>();
           nuovaLista.add(new IncassoProdotto(nome, ""+importo));
           databse.put(id, nuovaLista);
       }
    }

    public IncassoProdotto MaggioreIncasso(int id){
        String vinoCorr="";
        Double incassoTOT = 0.0;
        for(Map.Entry<Integer, ArrayList<IncassoProdotto>> entry: databse.entrySet()){
            if(entry.getKey() == id){
                ArrayList<IncassoProdotto> listaVini = entry.getValue();
                Double max = 0.0;
                for(int i = 0; i < listaVini.size(); i++){
                    vinoCorr = listaVini.get(i).getNome();
                    incassoTOT = Double.parseDouble(listaVini.get(i).getImporto());
                    for(int j = 0; j< listaVini.size(); j++){
                        if(listaVini.get(i).getNome().equals(listaVini.get(j).getNome()))
                            incassoTOT += Double.parseDouble(listaVini.get(j).getImporto());
                    }
                }
                if(max<incassoTOT){
                    max = incassoTOT;
                }
            }
        }
        return new IncassoProdotto(vinoCorr, ""+incassoTOT);
    }
}
