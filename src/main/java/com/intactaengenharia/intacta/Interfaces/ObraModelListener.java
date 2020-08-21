package com.intactaengenharia.intacta.Interfaces;

import com.intactaengenharia.intacta.Beans.Document;
import com.intactaengenharia.intacta.Beans.Obra;
import java.util.ArrayList;

public interface ObraModelListener {

    //void callback(ArrayList<Obra> obralist);
    interface OnCompleteObrasListener {
        void callbackObras(ArrayList<Obra> obralist);
    }
    interface OnCompleteObraListener {
        void callbackObra(Obra obra);
    }

    /*interface OnCompleteDocumentsListener {
        void callbackDocument(ArrayList<Document> documents);
    }*/

}
