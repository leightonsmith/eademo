package org.smith.eademo

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.URIBuilder
import javax.crypto.*
import javax.crypto.spec.*
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.JSON

class TwitterService {

    // Secrets requried to identify ourselves.
    String consumerSecret;
    String oauthTokenSecret;

    def getTweets() {
        
        // Note: this way of doing things actually works.
        // we can extend this to get somewhere...
        def http = new HTTPBuilder( 'http://api.twitter.com' )
        http.request( GET, JSON ) { req ->
            uri.path = '/1.1/search/tweets.json'
            uri.query = [ q: 'from:ea' ]
            headers.'Authorization' = 'OAuth oauth_consumer_key="08cnUGc55SxD0C5IixGG6w", oauth_nonce="fe4a7fe6c0373f232526bc9a6ef71270", oauth_signature="L9NRnvzcUFkgDiSBHd1KMpzf47Q%3D", oauth_signature_method="HMAC-SHA1", oauth_timestamp="1358717138", oauth_token="118162545-ggJen71XWj0GO9nkBUiwBkf6KSaC1PaqytzEnPfc", oauth_version="1.0"'

            response.success = { resp, json ->
                println "Query response: "
                println "response -->"
                println resp.dump()
                println "response <--"
                println "json -->"
                println json.dump()
                println "json <--"
            }
        }

        // String url = "http://api.twitter.com/1.1/search/tweets.json?q={}"
    }

    // Percent encode the given string. 
    // Note: this method doesn't work for internationalised characters, such as
    // 'À'
    String percentEncode(String val) {
        def encoded = val.collect { letter ->
            percentUnreserved[letter] ? letter : 
                '%' + Integer.toHexString((int) letter).toUpperCase()
        }
        return encoded.join('')
    }

    // Return a list of alphabetically sorted key:value pairs, 
    List sortMap(Map params) {
        def sorted = params.collect { k, v ->
            percentEncode(k) + "=" + percentEncode(v)
        }.sort()
        return sorted
    }

    // Create a string suitable for use as a signature base, according to twitter's rules
    String makeSignatureBase(String method, String baseUrl, Map params) {
        def sigVals = [percentEncode(method), percentEncode(baseUrl)]
        sigVals.addAll(percentEncode(sortMap(params).join('&')))
        return sigVals.join('&');
    }

    String makeSignature(String value) {
        String key = consumerSecret + "&" + oauthTokenSecret
        SecretKey skey = new SecretKeySpec(key.getBytes(), "HmacSHA1")
        Mac m = Mac.getInstance("HmacSHA1")
        m.init(skey)
        m.update(value.getBytes())
        byte[] mac = m.doFinal()
        return mac.encodeBase64().toString()
    }

    // Characters that don't have to be encoded under percent encoding.
    static Map percentUnreserved = [
'A':1, 'B':1, 'C':1, 'D':1, 'E':1, 'F':1, 'G':1, 'H':1, 'I':1, 'J':1, 'K':1, 'L':1, 'M':1, 'N':1, 'O':1, 'P':1, 'Q':1, 'R':1, 'S':1, 'T':1, 'U':1, 'V':1, 'W':1, 'X':1, 'Y':1, 'Z':1, 'a':1, 'b':1, 'c':1, 'd':1, 'e':1, 'f':1, 'g':1, 'h':1, 'i':1, 'j':1, 'k':1, 'l':1, 'm':1, 'n':1, 'o':1, 'p':1, 'q':1, 'r':1, 's':1, 't':1, 'u':1, 'v':1, 'w':1, 'x':1, 'y':1, 'z':1, '0':1, '1':1, '2':1, '3':1, '4':1, '5':1, '6':1, '7':1, '8':1, '9':1, '-':1, '_':1, '.':1, '~':1 ]
}
