package org.smith.eademo 


import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(TwitterService)
class TwitterServiceTests {

    void testPercentEncode() {
        assertEquals "from%3Aea", service.percentEncode("from:ea");
    }

    void testSortMap() {
        assertEquals "a=first", service.sortMap(["z":"last","a":"first"]).get(0)
    }

    void testSortEncoded() {
        assertEquals "%21=first", service.sortMap(["+":"last","!":"first"]).get(0)
        assertEquals "%2B=last", service.sortMap(["+":"last","!":"first"]).get(1)
    }

    void testSortAndEncodeMap() {
        assertEquals "%21=first&%2B=last", 
            service.sortMap(["+":"last","!":"first"]).join('&')
    }

    // taken from the twitter example docs
    void testMakeSignatureBase() {
        Map egMap = [
            status:  "Hello Ladies + Gentlemen, a signed OAuth request!",
            include_entities:    "true",
            oauth_consumer_key:  "xvz1evFS4wEEPTGEFPHBog",
            oauth_nonce: "kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg",
            oauth_signature_method:  "HMAC-SHA1",
            oauth_timestamp: "1318622958",
            oauth_token: "370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb",
            oauth_version:   "1.0"
        ]

        String egSig = "POST&https%3A%2F%2Fapi.twitter.com%2F1%2Fstatuses%2Fupdate.json&include_entities%3Dtrue%26oauth_consumer_key%3Dxvz1evFS4wEEPTGEFPHBog%26oauth_nonce%3DkYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1318622958%26oauth_token%3D370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb%26oauth_version%3D1.0%26status%3DHello%2520Ladies%2520%252B%2520Gentlemen%252C%2520a%2520signed%2520OAuth%2520request%2521"
        
        assertEquals egSig, service.makeSignatureBase(
            "POST", "https://api.twitter.com/1/statuses/update.json", egMap)
    }

    void testMakeSignature() {
        service.consumerSecret = "RKbTbRqPqjR2bCzM6MN0wCQI9v4woH5GjgzRNwcnUI"
        service.oauthTokenSecret = "tY5Hlsem7MwguVKeHlL4JVmG8WvNWu6TXTQaywZmI2Q" 

        String sig = service.makeSignature("GET&http%3A%2F%2Fapi.twitter.com%2F1.1%2Fsearch%2Ftweets.json&oauth_consumer_key%3D08cnUGc55SxD0C5IixGG6w%26oauth_nonce%3D15e407833297ffc67db94e7b243dce28%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1358736074%26oauth_token%3D118162545-ggJen71XWj0GO9nkBUiwBkf6KSaC1PaqytzEnPfc%26oauth_version%3D1.0%26q%3Dfrom%253Aea")

        assertEquals "4axXNn1LfUpsX%2FWSjlw9VNKEgDY%3D", service.percentEncode(sig)
    }

    void testMakeAuthHeader() {
        service.consumerSecret = "RKbTbRqPqjR2bCzM6MN0wCQI9v4woH5GjgzRNwcnUI"
        service.consumerKey = "08cnUGc55SxD0C5IixGG6w"

        service.oauthTokenSecret = "tY5Hlsem7MwguVKeHlL4JVmG8WvNWu6TXTQaywZmI2Q" 
        service.oauthTokenKey = "118162545-ggJen71XWj0GO9nkBUiwBkf6KSaC1PaqytzEnPfc" 
        service.baseUrl = "http://api.twitter.com" 
        service.metaClass.makeNonce = { "15e407833297ffc67db94e7b243dce28" }
        service.metaClass.makeTimestamp= { "1358736074" }

        String expectedAuth = 'OAuth oauth_consumer_key="08cnUGc55SxD0C5IixGG6w", oauth_nonce="15e407833297ffc67db94e7b243dce28", oauth_signature="4axXNn1LfUpsX%2FWSjlw9VNKEgDY%3D", oauth_signature_method="HMAC-SHA1", oauth_timestamp="1358736074", oauth_token="118162545-ggJen71XWj0GO9nkBUiwBkf6KSaC1PaqytzEnPfc", oauth_version="1.0"'
        String auth = service.makeAuthHeader(
            "GET", "/1.1/search/tweets.json", [q:'from:ea'])
        assertEquals expectedAuth, auth
    }

}
