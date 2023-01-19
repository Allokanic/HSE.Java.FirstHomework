package dev.homework;

public class Complex extends Number
{
    private double real;
    private double image;

    public Complex(Number value) {
        real = Double.parseDouble(value.toString());
    }

    public Complex(Number real, Number image) {
        this.real = Double.parseDouble(real.toString());
        this.image = Double.parseDouble(image.toString());
    }

    public Complex() {}

    public int intValue() {
        return (int)real;
    }

    public long longValue() {
        return (long)real;
    }

    public float floatValue() {
        return (float)real;
    }

    public double doubleValue() {
        return real;
    }

    public double getReal() {
        return real;
    }

    public double getImage() {
        return image;
    }

    public void setReal(Number value) {
        real = Double.parseDouble(value.toString());
    }

    public void setImage(Number value) {
        image = Double.parseDouble(value.toString());
    }

    public Complex add(Number value) {
        return new Complex(this.real + Double.parseDouble(value.toString()), this.image);
    }

    public Complex add(Complex value) {
        return new Complex(this.real + value.real, this.image + value.image);
    }

    public Complex subtract(Number value) {
        return new Complex(this.real - Double.parseDouble(value.toString()), this.image);
    }

    public Complex subtract(Complex value) {
        return new Complex(this.real - value.real, this.image - value.image);
    }

    public Complex multiply(Number value) {
        double mul = Double.parseDouble(value.toString());
        return new Complex(this.real * mul, this.image * mul);
    }

    public Complex multiply(Complex value) {
        return new Complex(
                this.real * value.real - this.image * value.image,
                this.real * value.image + this.image * value.real);
    }

    public Complex divide(Number value) {
        double div = Double.parseDouble(value.toString());
        return new Complex(this.real / div, this.image / div);
    }

    public Complex divide(Complex value) {
        double commonDivider = value.image * value.image + value.real * value.real;
        return new Complex(
                (this.real * value.real + this.image * value.image) / commonDivider,
                (this.image * value.real - this.real * value.image) / commonDivider);
    }

    public String toString() {
        return real + (image >= 0 ? "+" : "-") + image + "i";
    }
}
