<#ftl encoding="UTF-8">
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Thank you for registration ${username}</title>
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
                <h1 style="text-align: center">Registration</h1>
                <p>Hello, ${username}!</p>
                <p>This is a registration form. You can log in and use the online store application.</p>
                <p>To activate the shape cabinet, follow the link</p>
                <a href="https://oranger.store/api/auth/acvite/accouth?jwt=${jwt}">Oranger</a>

            </div>
        </div>
    </body>
</html>