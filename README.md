EA Demo project
=============

Small demo project, to demonstrate spring configuration, coding, and unit testing.

Spring Configuration
--------------------

Have used the spring security core and openid plugins to support openid login through google.

Coding & Unit Testing
---------------------

Wrote a custom service (org.smith.eademo.TwitterService) to fetch tweets from Twitter, in order to demonstrate a bit of coding and unit testing (usually I'd use a 3rd party library for common functionality like this). 

Building Notes
--------------

There were some problems in retrieving the google guice project (a dependency of the open-id plugin); it appears the repository suggested by the plugin is now password protected. A public repository has been added to BuildConfig; hopefully this resolves the problem.
