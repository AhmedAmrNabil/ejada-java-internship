import { Shape } from "./Shape";

class Circle extends Shape {
  constructor(
    x: number,
    y: number,
    private _radius: number,
  ) {
    super(x, y);
  }

  get radius(): number {
    return this._radius;
  }

  set radius(value: number) {
    this._radius = value;
  }
  toString(): string {
    return super.toString() + `, Radius: ${this._radius}`;
  }
  area(): number {
    return Math.PI * this._radius * this._radius;
  }
}

export { Circle };
