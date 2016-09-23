import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import us.monoid.web.Resty;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlVideo;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLAnchorElement;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLVideoElement;

public class PluralSight {

    public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);
        webClient.getOptions().setPopupBlockerEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        final HtmlPage page = webClient.getPage("https://www.pluralsight.com/training/Login?returnUrl=%2Ftraining&failedCaptcha=False");
        final HtmlTextInput username = page.getElementByName("UserHandle");
        final HtmlPasswordInput password= page.getElementByName("Password");
        final HtmlSubmitInput logIn = page.getElementByName("Login");

        username.setValueAttribute("jobin-tom");
        password.setValueAttribute("xNn8w2Xb");

        logIn.click();
        HtmlPage page3= webClient.getPage("http://pluralsight.com/training/courses/TableOfContents?courseName=java2&highlight=john-sonmez_java2-m5-annotations*2#java2-m5-annotations");
        //		List<?> links= page2.getByXPath("//*[@id=\"content\"]/div/div[2]/table/tbody/tr/td[1]/div/a");
        ////		System.out.println(links);
        int i=0;
        List<HtmlAnchor> links= page3.getAnchors();
        List<HtmlAnchor> videoLinks = new ArrayList<HtmlAnchor>();
        for(HtmlAnchor link:links){
            if(!link.getAttribute("ng-click").isEmpty() && !link.asText().isEmpty()){
                System.out.println(link.asText()+ "----> "+ i);
                videoLinks.add(link);
                i++;
            }
        }
        System.out.println(i);

        HtmlPage page2 = webClient.getPage("http://pluralsight.com/training/Player?author=john-sonmez&name=java2-m5-annotations&mode=live&clip=0&course=java2");
        //		System.out.println(page2.toString());
        //System.out.println(page2.asXml());
        int j=0;
        while(j<=i){
            String url = null;
            DomNodeList<DomElement> videolinks= page2.getElementsByTagName("video");
            for(DomElement link:videolinks)
                url= link.getAttribute("src");
            Resty r = new Resty();
            System.out.println(url);
            File s= r.bytes(url).save(new File("/Users/sreerajk/PluralSight2/Java-Fundementals-Part2-"+j+".mp4"));
            HtmlElement button = (HtmlElement) page2.getByXPath("//*[@id=\"videoControls\"]/div[8]").get(0);
            page2 = (HtmlPage) button.click();
            j++;
        }

        //		HtmlVideo videoLink = (HtmlVideo) videolinks.get(0);
        //		System.out.println(videoLink.getAttribute("src"));
        //		System.out.println(videolinks.size());
        //
//		HtmlPage page2= webClient.getPage("http://pluralsight.com/training/courses/TableOfContents?courseName=bash-shell-scripting&highlight=reindertjan-ekker_bash-shell-scripting-m1-intro*1!reindertjan-ekker_bash-shell-scripting-m9-func*1,2!reindertjan-ekker_bash-shell-scripting-m2-firstlook*1");
//		//				List<?> links= page2.getByXPath("//*[@id=\"content\"]/div/div[2]/table/tbody/tr/td[1]/div/a");
//		//		//		System.out.println(links);
//		int i=0;
//		List<HtmlAnchor> links= page2.getAnchors();
//		List<HtmlAnchor> videoLinks = new ArrayList<HtmlAnchor>();
//		for(HtmlAnchor link:links){
//			if(!link.getAttribute("ng-click").isEmpty() && !link.asText().isEmpty()){
//				System.out.println(link.asText()+ "----> "+ i);
//				videoLinks.add(link);
//				i++;
//			}
//		}
        //		HtmlPage page3= videoLinks.get(0).click();
        //		System.out.println(page3.getTitleText());
        //		HtmlPage page4 = (videoLinks.get(0).click()).getEnclosingPage()
        //		DomNodeList<DomElement> videolinks= page3.getElementsByTagName("video");
        //		System.out.println(videolinks.get(0).toString());
        //		System.out.println(videolinks.size());
        //System.out.println(videoLinks.size());
        //		System.out.println("count "+i);

    }

}