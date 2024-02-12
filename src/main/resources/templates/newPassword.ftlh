<#ftl encoding="UTF-8">
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Account activation, ${name}</title>
    <style>
        #body {
            display: flex;
            flex-direction: column;
            font-family: "Segoe UI", serif;
            align-items: center;
            background-color: #254499;
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
                <h1 style="text-align: center">New Password Profile</h1>
                <p>Hello, ${name}!</p>
                <p>With best regards, <br> Marketplace</p>
                <а id="a" href="https://marketplace-frontend-mu.vercel.app/update/password/{userId}"
                   style="text-align: center">Update Password</а>
            </div>
        </div>
    </body>
</html>