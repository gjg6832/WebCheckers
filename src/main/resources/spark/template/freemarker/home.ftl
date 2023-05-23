<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <div class="navigation">
    <#if userName??>
      <a href="/">my home</a> |
      <a href="/signout">sign out [${userName.name}]</a>
    <#else>
      <a href="/signin">sign in</a>
    </#if>
  </div>

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl">
      <!-- TODO: future content on the Home:
            spectating active games,
            or replay archived games
    -->

      <!-- if player is signed in. -->
      <#if userName??>

        <div class = "player list">
          <div>
            <br/>
            <p id = "availableUsers">
              Available Users
            </p>

            <!-- Buttons to challenge available players. -->
            <form action="./connect" method="POST">
              <#list playerList as player>
                <#if userName != player>
                  <button class = "player-username" name = "availablePlayer" type="submit" value = "${player.getName()}">${player.toString()}</button>
                </#if>
              </#list>
            </form>
          </div>
        </div>
      </#if>



  </div>

</div>
</body>

</html>
