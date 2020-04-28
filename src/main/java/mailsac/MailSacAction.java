package mailsac;

import helpers.AllureUIUtil;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.Assert;
import io.restassured.specification.RequestSpecification;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class MailSacAction {
    static {
        RequestSpecBuilder baseRequestBuilder = new RequestSpecBuilder();
        baseRequestBuilder
                .setBaseUri(String.format("https://%s/api/", BaseMailSacData.DOMAIN))
                .addParam("_mailsacKey", BaseMailSacData.KEY)
                .setRelaxedHTTPSValidation()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setUrlEncodingEnabled(false)
                .log(LogDetail.ALL).addFilter(new AllureRestAssured());

        RestAssured.requestSpecification = baseRequestBuilder.build();
    }

    private List<MailSacResponse> mailSacResponseList;

    public List<MailSacResponse> getMailSacResponseList() {
        return mailSacResponseList;
    }

    public MailSacAction(String email) {
        mailSacResponseList = findMailSacListByEmail(email);
    }

    @Step
    private List<MailSacResponse> findMailSacListByEmail(String email) {

        for (int i = 0; i < 15; i++) {
            MailSacResponse[] mailSacResponse = RestAssured
                    .get(String.format("/addresses/%s/messages", email))
                    .then()
                    .extract()
                    .response()
                    .as(MailSacResponse[].class);
            List<MailSacResponse> mailSacResponseList = Arrays.asList(mailSacResponse);

            if (mailSacResponseList.size() > 0) {
                return mailSacResponseList;
            } else {
                try {
                    sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Logger.getGlobal().log(Level.INFO, "Count of letters is: " + mailSacResponseList.size() + " ,expected count is 1");
                AllureUIUtil.paramNameValue("Count of letters is: " + mailSacResponseList.size() + " ,expected count is 1");
            }
        }
        throw new NoSuchElementException("No expected mail in folder");
    }


    @Step
    public String getActivateUrl() {
        Assert.assertTrue("PROM.UA link should be present, but null",
                mailSacResponseList.stream().anyMatch(mailSacResponse -> mailSacResponse.getLinks()
                        .stream().anyMatch(link -> link.contains("account/activate_email"))));

        String linkkkk = mailSacResponseList.stream()
                .filter(mailSacResponse -> mailSacResponse.getLinks().stream()
                        .anyMatch(link -> link.contains("account/activate_email")))
                .findFirst().get().getLinks()
                .stream().filter(s -> s.contains("account/activate_email")).findFirst().get();

        System.out.println(linkkkk);
        return linkkkk;
    }


}
