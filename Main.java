package org.example;

import java.util.Arrays;

public class Main {
  public interface CustomList<A> {

    /**
     * Добавляет элемент в конец списка.
     *
     * @param element, который должен быть добавлен
     * @throws IllegalArgumentException если элемент равен нулю
     */
    void add(A element);

    /**
     * Извлекает элемент в указанной позиции списка.
     *
     * @param index - индекс извлекаемого элемента.
     * @return элемент в указанное положение
     * @throws IndexOutOfBoundsException если индекс равен нулю
     */
    A get(int index);

    /**
     * Удаляет элемент в указанной позиции списка.
     * Все последующие элементы смещены влево.
     *
     * @param index - индекс элемента, который должен быть удален
     * @return  элемент, который ранее находился в указанной позиции
     * @throws IndexOutOfBoundsException если индекс вне диапазона
     */
    A remove(int index);
  }

  public static class CustomArrayList<A> implements CustomList<A> {

    private static final int DEFAULT_CAPACITY = 10;
    private A[] elements;
    private int size = 0;

    @SuppressWarnings("Непроверенный")
    public CustomArrayList() {
      elements = (A[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void add(A element) {
      if (element == null) {
        throw new IllegalArgumentException("Ошибка");
      }
      ensureCapacity();
      elements[size++] = element;
    }

    @Override
    public A get(int index) {
      if (index >= size || index < 0) {
        throw new IndexOutOfBoundsException("Вне диапазона: " + index);
      }
      return elements[index];
    }

    @Override
    public A remove(int index) {
      if (index >= size || index < 0) {
        throw new IndexOutOfBoundsException("Вне диапазона: " + index);
      }
      A removedElement = elements[index];
      shiftLeft(index);
      size--;
      elements[size] = null; // clear the last element
      return removedElement;
    }

    private void ensureCapacity() {
      if (size == elements.length) {
        elements = Arrays.copyOf(elements, elements.length * 2);
      }
    }

    private void shiftLeft(int index) {
      for (int i = index; i < size - 1; i++) {
        elements[i] = elements[i + 1];
      }
    }
    public void print() {
      for (int i = 0; i < size; i++) {
        System.out.print(elements[i] + " ");
      }
      System.out.println();
    }
  }
  public static void main(String[] args) {
    CustomArrayList<Integer> arr = new CustomArrayList<Integer>();
    arr.add(2);
    arr.add(5);
    arr.add(1);
    arr.add(4);
    arr.get(3);
    arr.remove(3);
    arr.print();
  }
}

