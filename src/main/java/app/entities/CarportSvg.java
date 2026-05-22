package app.entities;

public class CarportSvg
{
    private int width;
    private int length;
    private Svg carportSvg;

    public CarportSvg(int width, int length)
    {
        this.width = width;
        this.length = length;
        carportSvg = new Svg(0, 0, "0 0 855 690", "75%" );
        carportSvg.addRectangle(0,0,length, width, "stroke-width:1px; stroke:#000000; fill: #ffffff");
        addBeams();
        addRafters();
    }

    private void addBeams(){
        carportSvg.addRectangle(0,30,4.5, width, "stroke-width:1px; stroke:#000000; fill: #ffffff");
        carportSvg.addRectangle(0,length -30,4.5, width, "stroke-width:1px; stroke:#000000; fill: #ffffff");
    }

    private void addRafters(){
        for (double i = 0; i < width; i+= 55.714)
        {
            carportSvg.addRectangle(i, 0.0, length, 4.5,"stroke:#000000; fill: #ffffff" );
        }
    }

    @Override
    public String toString()
    {
        return carportSvg.toString();
    }
}
