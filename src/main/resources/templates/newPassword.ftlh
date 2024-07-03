<#ftl encoding="UTF-8">
<html lang="ru">
<head>
    <meta charset="UTF-8">
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
            margin: 10%;
            font-family: "Segoe UI", serif;
            background-color: #E8F6DC;
            padding: 3%;
            border-radius: 20px;
            color: #1B1E23;
        }
    </style>
</head>
    <body>
        <div id="body">
            <div id="div">
                <h1 style="text-align: center">Reset Password</h1>
                <p>Для зміни пароля перейдіть за посиланням, ${username}!</p>
                <a href="https://oranger.store/?auth_modal=true&chptoken=${jwt}">Update Password</a>
            </div>
        </div>
    </body>
</html>