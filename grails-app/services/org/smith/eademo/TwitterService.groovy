package org.smith.eademo

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.URIBuilder
import javax.crypto.*
import javax.crypto.spec.*
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.JSON

import java.security.SecureRandom
import java.math.BigInteger

class TwitterService {

    // Secrets requried to identify ourselves.
    String consumerSecret;
    String consumerKey;
    String oauthTokenSecret;
    String oauthTokenKey;
    String baseUrl


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
    String makeSignatureBase(String method, String path, Map params) {
        def sigVals = [percentEncode(method), percentEncode(path)]
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

    Map makeOAuthParams() {
        return [
            oauth_consumer_key: consumerKey,
            oauth_nonce: makeNonce(),
            oauth_signature_method:  "HMAC-SHA1",,
            oauth_timestamp: makeTimestamp(),
            oauth_token: oauthTokenKey,
            oauth_version:   "1.0"
        ]
    }

    def searchTweets(String queryExp) {
        String path =  '/1.1/search/tweets.json'
        Map query = [q:queryExp]

        // Note: this way of doing things actually works.
        // we can extend this to get somewhere...
        def http = new HTTPBuilder(baseUrl)
        http.request(GET, JSON) { req ->
            uri.path = '/1.1/search/tweets.json'
            uri.query = query
            headers.'Authorization' = makeAuthHeader("GET", path, query)

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

    String makeAuthHeader(String method, String path, Map queryParams)
    {
        Map oauthParams = makeOAuthParams();
        String base = makeSignatureBase(method, baseUrl + path, queryParams + oauthParams)
        String signature = percentEncode(makeSignature(base))
        return "OAuth " +
            "oauth_consumer_key=\"${oauthParams['oauth_consumer_key']}\", " +
            "oauth_nonce=\"${oauthParams['oauth_nonce']}\", " +
            "oauth_signature=\"${signature}\", " +
            "oauth_signature_method=\"${oauthParams['oauth_signature_method']}\", " +
            "oauth_timestamp=\"${oauthParams['oauth_timestamp']}\", " +
            "oauth_token=\"${oauthParams['oauth_token']}\", " +
            "oauth_version=\"${oauthParams['oauth_version']}\""
    }

    String makeNonce() {
        return UUID.randomUUID().toString().replaceAll('-','');
    }

    String makeTimestamp() {
        return (new Date().getTime() / 1000)
    }


    // Characters that don't have to be encoded under percent encoding.
    static Map percentUnreserved = [
'A':1, 'B':1, 'C':1, 'D':1, 'E':1, 'F':1, 'G':1, 'H':1, 'I':1, 'J':1, 'K':1, 'L':1, 'M':1, 'N':1, 'O':1, 'P':1, 'Q':1, 'R':1, 'S':1, 'T':1, 'U':1, 'V':1, 'W':1, 'X':1, 'Y':1, 'Z':1, 'a':1, 'b':1, 'c':1, 'd':1, 'e':1, 'f':1, 'g':1, 'h':1, 'i':1, 'j':1, 'k':1, 'l':1, 'm':1, 'n':1, 'o':1, 'p':1, 'q':1, 'r':1, 's':1, 't':1, 'u':1, 'v':1, 'w':1, 'x':1, 'y':1, 'z':1, '0':1, '1':1, '2':1, '3':1, '4':1, '5':1, '6':1, '7':1, '8':1, '9':1, '-':1, '_':1, '.':1, '~':1 ]

    static SecureRandom random = new SecureRandom();
}
