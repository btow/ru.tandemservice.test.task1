import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1>Задание №1</h1>
 * Реализуйте интерфейс {@link IStringRowsListSorter}.
 * <p>
 * <p>Мы будем обращать внимание в первую очередь на структуру кода и владение стандартными средствами java.</p>
 */
public class Task1Impl implements IStringRowsListSorter {

    // ваша реализация должна работать, как singleton. даже при использовании из нескольких потоков.
    public static final IStringRowsListSorter INSTANCE = new Task1Impl();

    /**
     * Сортирует переданный список записей (каждая запись - набор колонок) таблицы по указанной колонке по следующим правилам:
     * <ul>
     * <li>в колонке могут быть null и пустые значения - строки с null-значениями должны быть первыми,
     * затем строки с пустым значением, затем все остальные,</li>
     * <li>строка бьется на подстроки следующим образом: выделяем непрерывные максимальные фрагменты строки,
     * состоящие только из цифр, и считаем набором подстрок эти фрагменты
     * и все оставшиеся от такого разбиения фрагменты строки</li>
     * <li>при сравнении строк осуществляется последовательное сравнение их подстрок до первого несовпадения,</li>
     * <li>если обе подстроки состоят из цифр - то при сравнении они интерпретируются как целые числа
     * (вначале должно идти меньшее число), в противном случае - как строки,</li>
     * <li>сортировка должна быть устойчива к исходной сортировке списка - т.е., если строки (в контексте указанных правил сравнения)
     * неразличимы, то сортировка не должна менять их местами.</li>
     * </ul>
     *
     * @param rows        список записей таблицы (например, результат sql select), которые нужно отсортировать по указанной колонке
     * @param columnIndex индекс колонки, по которой нужно провести сортировку
     */
    @Override
    public void sort(final List<String[]> rows, final int columnIndex) {
        // напишите здесь свою реализацию. Мы ждем от вас хорошо структурированного, документированного и понятного кода.

        // включаем синхронизацию экземпляров класса
        synchronized (IStringRowsListSorter.class) {

            // вызываем метод сортировки списка с описанным компаратором
            rows.sort((o1, o2) -> {

                // определяем значения переменных, которые будут сравниваться
                String so1 = stringValue(o1, columnIndex),
                       so2 = stringValue(o2, columnIndex);

                /** проверяем строки на равенство null:
                 * если so1 == null, а so2 != null, то resultOfCompare = -1;
                 * если so1 == null и so2 == null, то resultOfCompare = 0;
                 * если so1 != null, а so2 == null, то resultOfCompare = 1;
                 */
                if (so1 == null & so2 != null) {
                    return  -1;
                } else if (so1 == null & so2 == null) {
                    return 0;
                } else if (so1 != null & so2 == null) {
                    return 1;
                }

                /** проверяем строки на содержание пустого значения:
                 * если so1 - пустое, а so2 - нет, то resultOfCompare = -1;
                 * если so1 - пустое и so2 - тоже, то resultOfCompare = 0;
                 * если so1 - НЕ пустое, а so2 - пусто, то resultOfCompare = 1;
                  */
                if (so1.isEmpty() & !so2.isEmpty()) {
                    return  -1;
                } else if (so1.isEmpty() & so2.isEmpty()) {
                    return 0;
                } else if (!so1.isEmpty() & so2.isEmpty()) {
                    return 1;
                }

                // определяем списки подстрок, извлекаемых из сравниваемых строк
                List<String> sao1 = extractSubstrings(so1),
                             sao2 = extractSubstrings(so2);

                /** если длина полученных списков разная, то определяем более коротский список
                 *  в качестве отправного при попарной синхронной сверке подстрок
                 */
                int minSizeOfList = (sao1.size() > sao2.size()) ? sao1.size() : sao2.size();

                // устанавливаем начальное значение возвращаемого результата сравнения
                int resultOfCampare = 0;

                // выполняем попарную синхронную сверку двух списков подстрок
                for (int i = 0; i < minSizeOfList; i++) {

                    // получаем значения подстрок
                    String ssao1 = sao1.get(i),
                            ssao2 = sao2.get(i);
                    //

                    // проверяем соответсвие подстрок шаблону \d+
                    Pattern regexp = Pattern.compile("\\d+");
                    Matcher m1 = regexp.matcher(ssao1),
                            m2 = regexp.matcher(ssao2);

                    // если обе подстроки соответствуют шаблону \d+, то сравниваем их как целые числа
                    if (m1.matches() & m2.matches()) {

                        /** если ssao1 < ssao2, тогда resultOfCompare = -1;
                         *  если ssao1 == ssao2, тогда resultOfCompare = 0;
                         *  если ssao1 > ssao2, тогда resultOfCompare = 1;
                         */
                        int isao1 = Integer.parseInt(ssao1),
                            isao2 = Integer.parseInt(ssao2);
                        resultOfCampare = (isao1 < isao2) ? -1 : (isao1 == isao2) ? 0 : 1;


                    } else {

                        /** если ssao1 < ssao2, тогда resultOfCompare = -1;
                         *  если ssao1 == ssao2, тогда resultOfCompare = 0;
                         *  если ssao1 > ssao2, тогда resultOfCompare = 1;
                         */
                        resultOfCampare = ssao1.compareTo(ssao2);
                        resultOfCampare = (resultOfCampare < 0) ? -1 : (resultOfCampare == 0) ? 0 : 1;

                    }

                    // при совпадении продолжаем сравнивать
                    if (resultOfCampare == 0) continue;

                    // при несовпадении возвращаем результат
                    return resultOfCampare;

                }

                return resultOfCampare;

            });
        }

    }

    /**
     * The method removes from the string the substrings and puts them into an list of strings
     *
     * @param so - the original string
     * @return - an array of substrings
     */
    private List<String> extractSubstrings(String so) {

        // объявляем пременные для выделения наибольшей последовательности из цифр
        String strSmoll = null, strBig = null;
        Pattern regexp = Pattern.compile("\\d+");
        Matcher m = regexp.matcher(so);

        while (m.find()) {

            strSmoll = m.group();

            if (strBig == null || strSmoll.length() > strBig.length()) {

                strBig = strSmoll;

            }

        }

        // определяем переменные для извлечения подстрок из исходной строки
        int indexBegin = 0,
                indexDivigion = (strBig == null) ? 0 : so.indexOf(strBig),
                indexEnd = (strBig == null) ? so.length() : indexDivigion + strBig.length();
        List<String> returnedList = new ArrayList<>();

        /** разбиваем строку на подстроки следующим образом:
         * 1) если найденная последовательность из цифр начинается НЕ с начала строки, то включаем в список подстрок всё,
         * что находится в строке до найденной последовательности цифр.
         */
        if (indexDivigion > 0) {
            returnedList.add(so.substring(indexBegin, indexDivigion));
        }

        /**
         * 2) если найдена последовательность из цифр, то включаем её в список подстрок,
         * а если она пуста, то включаем в список подстрок всю строку
         */
        if (strBig != null) {
            returnedList.add(strBig);
        } else {
            returnedList.add(so);
        }

        /**
         * 3) если после найденной последовательности из цифр имеются нецифровые символы,
         * то включаем их в список подстрок
         */
        if (indexEnd < so.length()) {
            returnedList.add(so.substring(indexEnd));
        }

        return returnedList;

    }

    /**
     * The method retrieves an array of strings, one string specified in the parameter
     *
     * @param rws         - the array of strings
     * @param columnIndex - the index number of the row in the string array
     * @return - extracted from an array of string or null if the index number is greater than the size of the array
     */
    private String stringValue(final String[] rws, final int columnIndex) {

        if (columnIndex >= rws.length) {
            return null;
        }

        return rws[columnIndex];

    }

}
