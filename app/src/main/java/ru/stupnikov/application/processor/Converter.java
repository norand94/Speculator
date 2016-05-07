package ru.stupnikov.application.processor;

import ru.stupnikov.application.orm_classes.Valuta;

/**
 * Created by rodion on 25.01.16.
 */
public  class Converter {



    public static double convertToValuta(Valuta transmitterValuta, double value, Valuta senderValuta){

        double dTransmitterValue = transmitterValuta.valueRUB;
        double dSenderValue = senderValuta.valueRUB;

        return (value*dTransmitterValue)/dSenderValue;
    }

    public static double convertToValuta (Double transmitterValue, double value, Double senderValue){

        return  transmitterValue*value/senderValue;
    }
}
