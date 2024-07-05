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
                <h1 style="text-align: center">Updated Password</h1>
                <p  style="text-align: center">Ви успішно змінили пароль, ${username}!</p>
                <a href="https://oranger.store/">Oranger</a>
            </div>
        </div>
    </body>
</html>