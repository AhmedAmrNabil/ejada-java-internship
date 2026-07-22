import { Shape } from "./Shape";

class Triangle extends Shape {
  constructor(
    x: number,
    y: number,
    private _base: number,
    private _height: number,
  ) {
    super(x, y);
  }

  get base(): number {
    return this._base;
  }

  get height(): number {
    return this._height;
  }

  set base(value: number) {
    this._base = value;
  }

  set height(value: number) {
    this._height = value;
  }
  toString(): string {
    return super.toString() + `, Base: ${this._base}, Height: ${this._height}`;
  }
  area(): number {
    return 0.5 * this._base * this._height;
  }
}

export { Triangle };
