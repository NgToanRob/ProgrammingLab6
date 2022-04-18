package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class testLog4j {

    final static Logger logger = LogManager.getLogger("ServerLogger");
//

    public static void main(String[] args) {

        logger.fatal("helooo");
//
//
//        logger.error("hihi");
//        logger.warn("can thanj nhye");

        System.out.println(testLog4j.class.getName());

    }


}
