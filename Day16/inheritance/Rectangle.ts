import { Shape } from "./Shape";

class Rectangle extends Shape {
  constructor(
    x: number,
    y: number,
    private _width: number,
    private _height: number,
  ) {
    super(x, y);
  }

  get width(): number {
    return this._width;
  }

  get height(): number {
    return this._height;
  }

  set width(value: number) {
    this._width = value;
  }

  set height(value: number) {
    this._height = value;
  }

  toString(): string {
    return (
      super.toString() + `, Width: ${this._width}, Height: ${this._height}`
    );
  }
  area(): number {
    return this._width * this._height;
  }
}

export { Rectangle };
