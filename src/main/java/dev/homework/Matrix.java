package dev.homework;

public class Matrix {
    private final Complex[][] matrix;
    private final int len;
    private final int wid;

    public Matrix(Complex[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            throw new IllegalArgumentException("Matrix len or wid should not be equal to 0");
        }
        len = matrix.length;
        wid = matrix[0].length;
        for(var elem : matrix) {
            if (elem.length != wid) {
                throw new IllegalArgumentException("Matrix should have a rectangular shape");
            }
        }
        this.matrix = new Complex[matrix.length][matrix[0].length];
        for (int i = 0; i < len; ++i) {
            if (wid >= 0) System.arraycopy(matrix[i], 0, this.matrix[i], 0, wid);
        }
    }

    public Matrix(int len, int wid) {
        matrix = new Complex[len][wid];
        for (int i = 0; i < len; ++i) {
            for (int j = 0; j < wid; ++j) {
                matrix[i][j] = new Complex();
            }
        }
        this.len = len;
        this.wid = wid;
    }

    public Matrix(Matrix another) {
        this.len = another.len;
        this.wid = another.wid;
        this.matrix = another.matrix.clone();
    }

    public int getLen() {
        return len;
    }

    public int getWid() {
        return wid;
    }

    public Complex get(int i, int j) {
        if (i < len && j < wid && i > -1 && j > -1) {
            return matrix[i][j];
        } else {
            throw new IndexOutOfBoundsException("Try to get element of the matrix with incorrect index");
        }
    }

    public void set(int i, int j, double value) {
        if (i < len && j < wid && i > -1 && j > -1) {
            matrix[i][j].setReal(value);
        } else {
            throw new IndexOutOfBoundsException("Try to set element of the matrix with incorrect index");
        }
    }

    public void set(int i, int j, Complex value) {
        if (i < len && j < wid && i > -1 && j > -1) {
            matrix[i][j] = value;
        } else {
            throw new IndexOutOfBoundsException("Try to set element of the matrix with incorrect index");
        }
    }

    private boolean checkSizeForEquality(Matrix another) {
        return !(another == null) && this.len == another.len && this.wid == another.wid;
    }

    public Matrix add(Matrix another) {
        if (!checkSizeForEquality(another)) {
            throw new IllegalArgumentException("An attempt to add different dimensions matrices");
        }
        Matrix result = new Matrix(this);
        for (int i = 0; i < len; ++i) {
            for (int j = 0; j < wid; ++j) {
                result.matrix[i][j].add(another.matrix[i][j]);
            }
        }
        return result;
    }

    public Matrix multiply(Matrix another) {
        if (wid != another.len) {
            throw new IllegalArgumentException("You can't multiply these matrices");
        }
        Matrix result = new Matrix(len, another.wid);
        for (int i = 0; i < len; ++i) {
            for (int j = 0; j < another.wid; ++j) {
                Complex resultTmp = new Complex();
                for (int k1 = 0; k1 < wid; ++k1) {
                    resultTmp = resultTmp.add(matrix[i][k1].multiply(another.matrix[k1][j]));
                }
                result.matrix[i][j] = resultTmp;
            }
        }
        return result;
    }

    public Matrix transpose() {
        Matrix result = new Matrix(wid, len);
        for (int i = 0; i < len; ++i) {
            for (int j = 0; j < wid; ++j) {
                result.matrix[j][i] = matrix[i][j];
            }
        }
        return result;
    }

    public Complex getDeterminant() {
        if (len != wid) {
            throw new RuntimeException("The determinant of this matrix can't be found");
        }
        Matrix tmp = new Matrix(this);
        Complex coef = new Complex(1);
        for (int i = 0; i < len - 1; ++i) {
            if (tmp.prepareLayer(i)) {
                for (int j = i + 1; j < len; ++j) {
                    if (tmp.matrix[j][i].getReal() != 0 || tmp.matrix[j][i].getImage() != 0) {
                        Complex mul = tmp.matrix[j][i].divide(tmp.matrix[i][i]);
                        coef = coef.multiply(mul);
                        tmp.multLine(i, mul);
                        tmp.subtractLines(j, i);
                    }
                    System.out.println(tmp);
                }
            } else {
                return new Complex(0, 0);
            }
        }
        Complex ans = new Complex(1);
        for (int i = 0; i < len; ++i) {
            ans = ans.multiply(tmp.matrix[i][i]);
        }
        ans = ans.divide(coef);
        return ans;
    }


    private void subtractLines(int from, int what) {
        for (int i = 0; i < wid; ++i) {
            matrix[from][i] = matrix[from][i].subtract(matrix[what][i]);
        }
    }

    private void multLine(int i, Complex mult) {
        for (int j = 0; j < wid; ++j) {
            matrix[i][j] = matrix[i][j].multiply(mult);
        }
    }

    private boolean prepareLayer(int layer) {
        if (matrix[layer][layer].getReal() == 0 && matrix[layer][layer].getImage() == 0) {
            for (int i = layer + 1; i < matrix.length; ++i) {
                if (matrix[i][layer].getReal() != 0 || matrix[i][layer].getImage() != 0) {
                    Complex[] tmp = matrix[i];
                    matrix[i] = matrix[layer];
                    matrix[layer] = tmp;
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; ++i) {
            for (int j = 0; j < wid; ++j) {
                stringBuilder.append(matrix[i][j].toString()).append("\t");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
