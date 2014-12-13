package com.avene.avene.livina.upnp;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.xml.Descriptor;
import org.fourthline.cling.model.message.header.STAllHeader;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

/**
 * Created by Avene on 12/13/2014.
 */
public class UPnPConnector {
    public static void main(String[] args) throws Exception {

        // UPnP discovery is asynchronous, we need a callback
        RegistryListener listener = new RegistryListener() {
            @Override
            public void remoteDeviceDiscoveryStarted(Registry registry, RemoteDevice device) {
                System.out.println("Discovery started: " + device.getDisplayString());
            }

            @Override
            public void remoteDeviceDiscoveryFailed(Registry registry, RemoteDevice device, Exception ex) {
                System.out.println("Discovery failed: " + device.getDisplayString() + " => " + ex);
            }

            @Override
            public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
                System.out.println("Remote device available: " + device.getDisplayString());
            }

            @Override
            public void remoteDeviceUpdated(Registry registry, RemoteDevice device) {
                System.out.println("Remote device updated: " + device.getDisplayString());
            }

            @Override
            public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
                System.out.println("Remote device removed: " + device.getDisplayString());
            }

            @Override
            public void localDeviceAdded(Registry registry, LocalDevice device) {
                System.out.println("Local device added: " + device.getDisplayString());
            }

            @Override
            public void localDeviceRemoved(Registry registry, LocalDevice device) {
                System.out.println("Local device removed: " + device.getDisplayString());
            }

            @Override
            public void beforeShutdown(Registry registry) {
                System.out.println("Before shutdown, the registry has devices: ");
                for(Device d :registry.getDevices()){
                    System.out.println(d.getDisplayString());
                }
            }

            @Override
            public void afterShutdown() {
                System.out.println("Shutdown of registry complete!");
            }
        };

        // This will create necessary network resources for UPnP right away
        System.out.println("Starting Cling...");
        UpnpService upnpService = new UpnpServiceImpl(listener);

        // Send a search message to all devices and services, they should respond soon
        upnpService.getControlPoint().search(new STAllHeader());

        // Let's wait 10 seconds for them to respond
        System.out.println("Waiting 10 seconds before shutting down...");
        Thread.sleep(10000);

        // Release all resources and advertise BYEBYE to other UPnP devices
        System.out.println("Stopping Cling...");
        upnpService.shutdown();
    }

}
