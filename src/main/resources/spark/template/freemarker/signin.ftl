<!DOCTYPE html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>Web Checkers | Sign In</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

    <h1>Web Checkers | Sign In</h1>

    <div class="body">

        <!-- Provide a message to the user, if supplied. -->
        <#include "message.ftl">

        <!-- Provide the user a way to enter their name -->
        <form action="./signin" method="POST">
            <br/>
            <input name="userName" />
            <br/><br/>
            <button type="submit">Log In</button>
        </form>
    </div>

</div>
</body>

</html>