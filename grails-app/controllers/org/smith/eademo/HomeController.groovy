package org.smith.eademo

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER','ROLE_ADMIN'])
class HomeController {
    def twitterService 

    def index() { 
        def tweets = twitterService.searchTweets('from:easportsfifa')
        [tweets:tweets]
    }

}
