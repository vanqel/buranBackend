package com.gamestore.gamestorebackendkotlin.auth.validation

/**
 * Хранилище значений и регулярных выражений для валидаторовв
 */
object ValidationProps {
    /**  LowerCaseLetter - Положительный предварительный просмотр, чтобы соответствовать ровно 3 строчным буквам */
    val LowerCaseLetter = "(?=(?:.*[a-z]){1})".toRegex()

    /**  UpperCaseLetter - Положительный предварительный просмотр, чтобы совпадали ровно 2 заглавные буквы */
    val UpperCaseLetter = "(?=(?:.*[A-Z]){1})".toRegex()

    /**  SpecialCaseLetter - Положительный предварительный просмотр, соответствующий ровно 1 специальному символу */
    val SpecialCaseLetter = "(?=.*[?!@#*%^&-+.,])".toRegex()

    /**  DecimalCase - Положительный предварительный просмотр, соответствующий ровно 2 числам
     * */
    val DecimalCase = "(?=(?:.*\\d){1})".toRegex()

    /**  EmailCase - Cоответсвие формату text@text.text */
    val EmailCase = "^[a-zA-Z0-9.!#\$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\$".toRegex()

    /**  PhoneCaseRU - Соответсвие первой цифры телефона страны Россия */
    val PhoneCaseRU = "^((8|\\+7)[\\- ]?)".toRegex()

    /**
     *   VALIDATION_MSG_USER - Заголовок ошибки при валидавции пользователя
     */
    const val VALIDATION_MSG_USER = "Ошибка валидации данных пользователя"

    /**
     *   VALIDATION_MSG_USER - Заголовок ошибки при валидавции токена
     */
    const val VALIDATION_MSG_TOKEN = "Ошибка валидации токена"

    /**
     *   LENGTH_USERNAME - Длина логина
     */
    const val LENGTH_USERNAME = 6

    /**
     *   LENGTH_PASSWORD - Длина пароля
     */
    const val LENGTH_PASSWORD = 5

    /**
     *   LENGTH_PHONE - Длина номера телефона
     */
    const val LENGTH_PHONE = 11
}
