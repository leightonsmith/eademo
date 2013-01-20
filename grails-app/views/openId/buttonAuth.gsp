<head>
  <title>Login</title>
  <style type='text/css' media='screen'>
    body {
      background: #202020;
      font-family: Arial;
    }

    div.openid-loginbox {
      width: 450px;
      margin-left: auto;
      margin-right: auto;
      background: white;
      padding: 15px;
    }

    .openid-loginbox-inner {
      width: 450px;
      border: 1px blue solid;
    }

    td.openid-loginbox-title {
      background: #e0e0ff;
      border-bottom: 1px #c0c0ff solid;
      padding: 0;
    }

    td.openid-loginbox-title table {
      width: 100%;
      font-size: 18px;
    }
    .openid-loginbox-useopenid {
      font-weight: normal;
      font-size: 14px;
    }
    td.openid-loginbox-title img {
      border: 0;
      vertical-align: middle;
      padding-right: 3px;
    }
    table.openid-loginbox-userpass {
      margin: 3px 3px 3px 8px;
    }
    table.openid-loginbox-userpass td {
      height: 25px;
    }
    input.openid-identifier {
      background: url(http://stat.livejournal.com/img/openid-inputicon.gif) no-repeat;
      background-color: #fff;
      background-position: 0 50%;
      padding-left: 18px;
    }

    input[type='text'],input[type='password'] {
      font-size: 16px;
      width: 310px;
    }
    input[type='submit'] {
      font-size: 14px;
    }

    td.openid-submit {
      padding: 3px;
    }

  </style>
</head>

<body>

  <div class="openid-loginbox">

    <g:if test='${flash.message}'>
    <div class='login_message'>${flash.message}</div>
    </g:if>

    <table class='openid-loginbox-inner' cellpadding="0" cellspacing="0">
      <tr>
        <td class="openid-loginbox-title">
          <table>
            <tr>
              <td align="left">Log in using: </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td>

          <div id='openidLogin'>
            <table class="openid-loginbox-userpass">
              <tr>
                <td>
                  <form action='${openIdPostUrl}' method='POST' autocomplete='off' name='openIdLoginForm'>
                    <input type="submit" value="Google" />
                    <input type="hidden" name="${openidIdentifier}" value="https://www.google.com/accounts/o8/id"/></td>
                </form>

              </tr>
            </table>
          </div>


        </td>
      </tr>
    </table>
  </div>
</body>
