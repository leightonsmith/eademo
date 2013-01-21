<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Welcom to a demo</title>
		<style type="text/css" media="screen">
			#status {
				background-color: #eee;
				border: .2em solid #fff;
				margin: 2em 2em 1em;
				padding: 1em;
				width: 22em;
				float: left;
				-moz-box-shadow: 0px 0px 1.25em #ccc;
				-webkit-box-shadow: 0px 0px 1.25em #ccc;
				box-shadow: 0px 0px 1.25em #ccc;
				-moz-border-radius: 0.6em;
				-webkit-border-radius: 0.6em;
				border-radius: 0.6em;
			}

			.ie6 #status {
				display: inline; /* float double margin fix http://www.positioniseverything.net/explorer/doubled-margin.html */
			}

			#status ul {
				font-size: 0.9em;
				list-style-type: none;
				margin-bottom: 0.6em;
			}
            
			#status li {
				line-height: 1.3;
				border-bottom: .2em solid #fff;
				padding: 1em;
			}

			#status h1 {
				text-transform: uppercase;
				font-size: 1.1em;
				margin: 0 0 0.3em;
			}

			#page-body {
				margin: 2em 1em 1.25em 18em;
			}

			h2 {
				margin-top: 1em;
				margin-bottom: 0.3em;
				font-size: 1em;
			}

			p {
				line-height: 1.5;
				margin: 0.25em 0;
			}

			#controller-list ul {
				list-style-position: inside;
			}

			#controller-list li {
				line-height: 1.3;
				list-style-position: inside;
				margin: 0.25em 0;
			}

			@media screen and (max-width: 480px) {
				#status {
					display: none;
				}

				#page-body {
					margin: 0 1em 1em;
				}

				#page-body h1 {
					margin-top: 0;
				}
			}
		</style>
	</head>
	<body>
		<a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="status" role="complementary">
			<h1>Tweets</h1>
            <ul>
            <g:each var="tweet" in="${tweets}">
                <li>${tweet.text}</li>
            </g:each>
            </ul>
		</div>
		<div id="page-body" role="main">
			<h1>EA Demo</h1>
            <p>A demo of some grails &amp; spring for EA</p>
            <p>This controller is secured by an openid provider (just google for now), hence demonstrating a use of the spring security plugin for grails. </p>
            <p>To the left is a list of tweets from @easportsfifa, fetched via a custom service written for this demo. The service was written to provide a code and unit testing sample.</p>
            <p>The styling should be pretty familiar; the default grails templates have been used, as the emphasis was on showing spring configuration and some unit tested code.</p>
		</div>
	</body>
</html>
