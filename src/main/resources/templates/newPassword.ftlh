<#ftl encoding="UTF-8">
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Account activation, ${username}</title>
    <style>
        #body {
            display: flex;
            flex-direction: column;
            font-family: "Segoe UI", serif;
            align-items: center;
            background-color: #FF8A00;
            font-size: 20px;
            height: 100%;
        }

        #div {
            display: block;
            flex-direction: column;
            margin: 10%;
            font-family: "Segoe UI", serif;
            background-color: #E8F6DC;
            padding: 3%;
            border-radius: 20px;
            color: #1B1E23;
        }

        #a {
            text-decoration: none;
        }
    </style>
</head>
    <body>
        <div id="body">
            <div id="div">
                <h1 style="text-align: center">Reset Password</h1>
                <p>Для зміни пароля перейдіть за посиланням, ${username}!</p>
                <а id="a" href="https://oranger.store/update/password/#{userId}">Update Password</а>
            </div>
        </div>
    </body>
</html>