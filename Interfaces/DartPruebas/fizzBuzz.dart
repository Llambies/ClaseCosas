void printFizzBuzz() {
  for (int i = 1; i <= 100; i++) {
    if (i % 3 == 0 && i % 5 == 0) {
      print("BuzzFizz");
    } else if (i % 3 == 0) {
      print("Buzz");
    } else if (i % 5 == 0) {
      print("Fizz");
    } else {
      print(i);
    }
  }
}

void main(List<String> args) {
  printFizzBuzz();
}
