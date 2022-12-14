package com.threadingbasics.service;

import com.threadingbasics.model.PhotoFrames;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PhotoFrameService {
    Map<String, List<PhotoFrames>> availableFrames = new HashMap<>();

    public boolean isAvailable(String type) {
        synchronized (availableFrames) {
            if (availableFrames.containsKey(type) && !availableFrames.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " This frame is currently available in stock :)");
                return true;
            }
            System.out.println(Thread.currentThread().getName() + " :" +
                    " OUT OF STOCK");
            return false;
        }
    }

    public void buyStock(String type) {
        synchronized (availableFrames) {
            if (!availableFrames.isEmpty() && availableFrames.containsKey(type)) {
                List<PhotoFrames> PhotoFramesList = availableFrames.remove(type);
                PhotoFrames photoFrames = PhotoFramesList.remove(0);
                availableFrames.put(type, PhotoFramesList);
                System.out.println(Thread.currentThread().getName() + ":PhotoFrame :" + photoFrames.id + " Will be delivered soon");
            } else {
                System.out.println(Thread.currentThread().getName() + " : This Frame is out of stock");
            }
        }
    }

    //    This method send notification to customers about hte product availability
    public void updateProductAvailabilityToBuyer(String type) {
        synchronized (availableFrames) {
            while (availableFrames.isEmpty() && !availableFrames.containsKey(type)) {
                try {
                    availableFrames.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " :UpdateBuyer : This Photo Frame is BACK IN STOCK");
        }
    }

    public void updateAvailabilityOnUi(String type) {
        synchronized (availableFrames) {
            while (availableFrames.isEmpty() && availableFrames.containsKey(type)) {
                try {
                    System.out.println(Thread.currentThread().getName() +
                            " : currently this PhotoFrame is out of stock...waiting for update");
                    availableFrames.wait();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
//            Item is back in stock so update stock
            System.out.println(Thread.currentThread().getName() + " : This PhotoFrame is available in stock again");
        }
    }

    public void putStock(PhotoFrames photoFrame) {
        synchronized (availableFrames) {
            String type = "c" + photoFrame.getColor() + "-t" + photoFrame.getTexture();
            if (availableFrames.containsKey(type)) {
//                if key already available in photoFrame then only put the value
                List<PhotoFrames> photoFramesList = availableFrames.get(type);
                photoFramesList.add(photoFrame);
                availableFrames.put(type, photoFramesList);
            } else {
//                if key not present in availableFrames add key then value
                List<PhotoFrames> list = new ArrayList<>();
                list.add(photoFrame);
                availableFrames.put(type, list);
            }
            availableFrames.notifyAll();
            System.out.println(Thread.currentThread().getName() + " : Photoframe added in stock...notiying buyers");
        }
    }
}
