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
                <h1 style="text-align: center">Шановний, ${username}!</h1>
                <p>Ми раді повідомити Вам про успішну реєстрацію на нашому маркетплейсі Oranger.
                    Ваш обліковий запис був створений і готовий до використання.
                    Тепер Ви можете увійти на маркетплейс використовуючи логін і пароль,
                    які були вказали під час реєстрації.</p>

                <p>Ми сподіваємося, що Ви отримаєте задоволення від використання нашого сервісу</p>

                <p>З повагою, Команда <a href="https://oranger.store/?reg_token=${jwt}">Oranger</a></p>
            </div>
        </div>
    </body>
</html>