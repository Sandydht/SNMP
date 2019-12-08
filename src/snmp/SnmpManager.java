/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snmp;

import java.io.IOException;
import java.util.ArrayList;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
 *
 * @author Sandy
 */
public class SnmpManager implements ISnmpManager {
    
    private String community;
    private ArrayList<String> listOfAgents;

    public SnmpManager() {
        listOfAgents = new ArrayList<>();
        community = "public";
    }

    @Override
    public boolean isAgent(String newCommunity, String ip) { // +/161
        try {
            CommunityTarget target = buildTarget(newCommunity, ip);
            PDU request = new PDU();
            request.setType(PDU.GET);
            //OID oid = new OID("1.3.6.1.2.1.1.1.0"); // sysDescr 
            //request.add(new VariableBinding(oid));
            try {
                Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
                snmp.listen();
                ResponseEvent response = snmp.send(request, target);
                if (response.getResponse() != null) {
                    return true;
                } else {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }

    @Override
    public String getObjectAsString(String oid, String ip) { // +161
        try {
            CommunityTarget target = this.buildTarget(community, ip);
            String responseString = "";
            PDU request = new PDU();
            request.setType(PDU.GET);
            OID oidObj = new OID(oid);
            request.add(new VariableBinding(oidObj));
            try {
                Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
                snmp.listen();
                ResponseEvent responseEvent = snmp.send(request, target);
                PDU responsePDU = responseEvent.getResponse();
                if (responsePDU != null) {
                    responseString = responsePDU.getVariableBindings().toString();
                    int errorStatus = responsePDU.getErrorStatus();
                    int errorIndex = responsePDU.getErrorIndex();
                    String errorStatusText = responsePDU.getErrorStatusText();
                    if(errorStatus != PDU.noError) {
                        responseString = "Error: Request Failed\n" +
                                        "Error Status = " + errorStatus + "\n" +
                                        "Error Index = " + errorIndex + "\n" +
                                        "Error Status Text = " + errorStatusText;
                    }
                }
            } catch (IOException e) {
            }
            return responseString;
        } catch (NumberFormatException nfe) {
            return "Invalid OID";
        }
    }
    
    private CommunityTarget buildTarget(String newCommunity, String ip) { // +/161
        CommunityTarget target = new CommunityTarget();
        Address targetAddress = new UdpAddress(ip);
        target.setCommunity(new OctetString(newCommunity));
        target.setAddress(targetAddress);
        target.setRetries(1);
        target.setTimeout(100);
        target.setVersion(SnmpConstants.version2c);
        return target;
    }
}
