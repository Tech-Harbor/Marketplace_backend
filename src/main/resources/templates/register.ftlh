<#ftl encoding="UTF-8">
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Thank you for registration ${name}</title>
    <style>
        #body {
            display: flex;
            flex-direction: column;
            font-family: "Segoe UI", serif;
            align-items: center;
            background-color: #607FF2;
            font-size: 20px;
            height: 100%;
        }

        #div {
            display: block;
            flex-direction: column;
            margin: 10%;
            font-family: "Segoe UI", serif;
            background-color: white;
            padding: 3%;
            border-radius: 20px;
            color: black;
        }

        #a {
            text-decoration: none;
        }
    </style>
</head>
    <body>
        <div id="body">
            <div id="div">
                <h1 style="text-align: center">Registration</h1>
                <p>Hello, ${name}!</p>
                <p>This is a registration form. You can log in and use the online store application.</p>
                <p>With best regards, <br> Marketplace</p>
                <Button id="a" href="https://github.com/Vladik-gif" style="text-align: center">Marketplace</Button>
            </div>
        </div>
    </body>
</html>