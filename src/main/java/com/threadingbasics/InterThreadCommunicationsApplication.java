package com.threadingbasics;

import com.threadingbasics.model.PhotoFrames;
import com.threadingbasics.service.PhotoFrameService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InterThreadCommunicationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterThreadCommunicationsApplication.class, args);

        PhotoFrameService photoFrameService = new PhotoFrameService();
//        creating UI thread
        Thread UIthread = new Thread(new Runnable() {
            @Override
            public void run() {
                String type = "cBrown-tCrack";
                photoFrameService.updateAvailabilityOnUi(type);
            }
        }, "UIthread");
        UIthread.start();

        //    creating Buyer Thread
        Thread buyerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String type = "cBrown-tCrack";
                if (photoFrameService.isAvailable(type)) {
                    photoFrameService.buyStock(type);
                } else {
                    photoFrameService.updateProductAvailabilityToBuyer(type);
                }
            }
        }, "buyerThread");
        buyerThread.start();

//        Creating seller thread
        Thread sellerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String type = "cBrown-tCrack";
                PhotoFrames newPhotoFrame = new PhotoFrames(1, "wooden", "4x6", "Crack", "Brown");
                photoFrameService.putStock(newPhotoFrame);
            }
        }, "sellerThread");
        sellerThread.start();
    }


}
