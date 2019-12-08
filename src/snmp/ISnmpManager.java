/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snmp;

import java.util.ArrayList;

/**
 *
 * @author Sandy
 */
public interface ISnmpManager {

//    ArrayList<String> getListOfAgents();

    String getObjectAsString(String oid, String ip);

    boolean isAgent(String community, String ipAddress);
//    void setChosenIp(String ip);
}
