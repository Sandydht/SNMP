/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import snmp.ISnmpManager;
import snmp.SnmpManager;

/**
 *
 * @author Sandy
 */
public class SnmpController {
     ISnmpManager snmpManager;

    public SnmpController() {
        snmpManager = new SnmpManager();
    }

    public boolean isAgent(String community, String ip) { // -/161
        return snmpManager.isAgent(community, (ip + "/161"));
    }

    public String snmpFunctionsToString(String community, String function, String object, String ipTarget, String setvalue) {
        String str;
        switch (function) {
            case "GET":
                str = snmpManager.getObjectAsString(object, (ipTarget + "/161"));
                break;
            default:
                str = "";
        }
        return str;
    }
    
    public String translateObjectToOID(String object) {
        String oid;
        switch (object.toLowerCase()) {
            case "description":
                oid = "1.3.6.1.2.1.1.1.0";
                break;
            case "uptime":
                oid = "1.3.6.1.2.1.1.3.0";
                break;
            case "contact":
                oid = "1.3.6.1.2.1.1.4.0";
                break;
            case "name":
                oid = "1.3.6.1.2.1.1.5.0";
                break;
            case "location":
                oid = "1.3.6.1.2.1.1.6.0";
                break;
            case "totalhddspace":
                oid = "1.3.6.1.2.1.25.2.3.1.5.1";
                break;
            case "usedhddspace":
                oid = "1.3.6.1.2.1.25.2.3.1.6.1";
                break;
            case "totalmemory":
                oid = "1.3.6.1.2.1.25.2.3.1.5.2";
                break;
            case "usedmemory":
                oid = "1.3.6.1.2.1.25.2.3.1.6.2";
                break;
            default:
                oid = "";
        }
        return oid;
    }
}
