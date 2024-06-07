import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your expression:");
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            try {
                System.out.println(calc(input));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }

    public static String calc(String input) throws Exception {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new Exception("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        String leftOperand = parts[0];
        String operator = parts[1];
        String rightOperand = parts[2];

        boolean isLeftRoman = isRoman(leftOperand);
        boolean isRightRoman = isRoman(rightOperand);

        if (isLeftRoman != isRightRoman) {
            throw new Exception("throws Exception //т.к. используются одновременно разные системы счисления");
        }

        int leftNumber;
        int rightNumber;

        if (isLeftRoman) {
            leftNumber = romanToArabic(leftOperand);
            rightNumber = romanToArabic(rightOperand);
        } else {
            leftNumber = Integer.parseInt(leftOperand);
            rightNumber = Integer.parseInt(rightOperand);
        }

        if (leftNumber < 1 || leftNumber > 10 || rightNumber < 1 || rightNumber > 10) {
            throw new Exception("throws Exception //т.к. числа должны быть в диапазоне от 1 до 10 включительно");
        }

        int result;

        switch (operator) {
            case "+":
                result = leftNumber + rightNumber;
                break;
            case "-":
                result = leftNumber - rightNumber;
                if (isLeftRoman && result <= 0) {
                    throw new Exception("throws Exception //т.к. в римской системе нет отрицательных чисел");
                }
                break;
            case "*":
                result = leftNumber * rightNumber;
                break;
            case "/":
                if (rightNumber == 0) {
                    throw new Exception("throws Exception // Деление на ноль");
                }
                result = leftNumber / rightNumber;
                break;
            default:
                throw new Exception("throws Exception // Неподдерживаемый оператор");
        }

        if (isLeftRoman) {
            if (result <= 0) {
                throw new Exception("throws Exception //т.к. в римской системе нет отрицательных чисел");
            }
            return arabicToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    private static boolean isRoman(String value) {
        return value.matches("^(?=[MDCLXVI])M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
    }

    private static int romanToArabic(String roman) throws Exception {
        int result = 0;
        int previousValue = 0;
        for (char c : roman.toCharArray()) {
            int currentValue = romanCharToArabic(c);
            result += currentValue;
            if (currentValue > previousValue) {
                result -= 2 * previousValue;
            }
            previousValue = currentValue;
        }
        return result;
    }

    private static int romanCharToArabic(char romanChar) throws Exception {
        switch (romanChar) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                throw new Exception("Invalid Roman numeral: " + romanChar);
        }
    }

    private static String arabicToRoman(int number) {
        StringBuilder result = new StringBuilder();
        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanNumerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int i = 0; i < arabicValues.length; i++) {
            while (number >= arabicValues[i]) {
                number -= arabicValues[i];
                result.append(romanNumerals[i]);
            }
        }
        return result.toString();
    }
}