package com.thunderfinesse.thunderskillfetcher.player.html;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * push the "update statistics" button on thunderskill
 * {@link <a href="https://thunderskill.com/en/stat/Luigi012">players site</a>}
 */
public class UpdateButton {
    private String errorMsg;

    public int pushButton(String login){
        // System Property for IEDriver
        System.setProperty("webdriver.edge.driver", "src/main/resources/msedgedriver.exe");
        // Instantiate a IEDriver class.
        WebDriver driver = new EdgeDriver();
        // Launch Website
        driver.navigate().to("https://thunderskill.com/en/stat/"+login);

        //find update button
        try {
            WebElement updateButton = driver.findElement(By.className("btn-primary"));
            updateButton.click();
            driver.quit();
            return 0;
        }catch (NoSuchElementException e){
            //if it couldn't find button then it means button was already pushed
            //(after clicking button you need to wait 24h before next possibility to click)
            try {

                String text = driver.findElement(By.className("next_update")).getText();

                //parsing hours and minute to next update
//                Pattern pattern = Pattern.compile("([\\d])+ h. ([\\d])+ min.");
//                Matcher matcher = pattern.matcher(text);
//                matcher.find();
//                errorMsg = "update button already pushed, next update available in: " + matcher.group();

                errorMsg = text;
                System.err.println(errorMsg);
                driver.quit();
                return -1;
            }catch (NoSuchElementException e1){
                //if it couldn't find information about next update then it means such login does not exist
                errorMsg = driver.findElement(By.className("alert-danger")).getText();
                System.err.println(errorMsg);
                driver.quit();
                return -2;
            }
        }
    }
}
