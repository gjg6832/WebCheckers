 <div class="navigation">
  <#if currentUser??>
    <a href="/">my home</a> |
    <a href="/signout">sign out [${currentUser.name}]</a>
  <#else>
    <a href="/signin">sign in</a>
  </#if>
 </div>
