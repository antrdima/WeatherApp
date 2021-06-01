## Тестовое задание для Андройд разработчика

Прототип экрана авторизации с получением геоположение и показом местной погоды  
Используемый стек: Kotlin, MVVM, Hilt, Retrofit  
Тестовое и пример работы в папке sample  

<img src="https://raw.githubusercontent.com/AntropovD/WeatherPrototype/main/sample/Screenshot_2021-05-27-12-28-15-192_org.example.weatherprototype.jpg" width="200" height="400"><img src="https://raw.githubusercontent.com/AntropovD/WeatherPrototype/main/sample/Screenshot_2021-05-27-12-28-03-605_org.example.weatherprototype.jpg" width="200" height="400">

### Минусы
1. Использование databinding. рекомендуют отказаться от него в пользу viewbinding 

    * https://www.reddit.com/r/androiddev/comments/fygd5l/is_data_binding_worth/fmztciy?utm_source=share&utm_medium=web2x&context=344
    * https://www.reddit.com/r/androiddev/comments/eyg806/data_binding/fgi5mtn/

2. Вынести часть логики из активити в утилиты, например получение локации

3. Проверить темную тему. Вроде отключал ее использование
