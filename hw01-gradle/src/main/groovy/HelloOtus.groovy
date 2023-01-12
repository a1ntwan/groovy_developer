import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

class HelloOtus {

  static void main(String[] args) {
    println('Hello Otus part from Google Guava:')

    Map items = ImmutableMap.of("rubber", 3, "pen", 4, "pencil", 1);

    items.entrySet()
            .stream()
            .forEach(System.out::println);

    List<String> fruits = Lists.newArrayList("orange", "banana", "kiwi",
            "mango", "date", "quince");

    for (String fruit: fruits) {
      println(fruit);
    }
  }
}