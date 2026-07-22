import { Circle } from "./Circle";
import { Rectangle } from "./Rectangle";
import { Shape } from "./Shape";
import { Triangle } from "./Triangle";

let shapes: Shape[] = [
  new Circle(0, 1, 5),
  new Rectangle(2, 0, 10, 5),
  new Triangle(5, 3, 8, 6),
];

shapes.forEach((shape) => {
  console.log(shape.toString(), `Area: ${shape.area()}`);
});
