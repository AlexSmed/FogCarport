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
        carportSvg = new Svg(100, 20, "0 0 855 690", "75%" );
        carportSvg.addRectangle(100,20,length, width, "stroke-width:1px; stroke:#000000; fill: #ffffff");
        carportSvg.addArrow( 80, length+20,80, 20,
                "stroke:#000000; stroke-width:2");
        carportSvg.addArrow(100, length +40 , width+100, length + 40,
                "stroke:#000000; stroke-width:2");
        carportSvg.addText((width+100) / 2,length + 70, 0,String.valueOf(Double.valueOf((double) width / 100)));
        carportSvg.addText(50, (length+20) / 2, 0, String.valueOf(Double.valueOf((double) length / 100)));
        addBeams();
        addRafters();
    }

    private void addBeams(){
        carportSvg.addRectangle(100,50,4.5, width, "stroke-width:1px; stroke:#000000; fill: #ffffff");
        carportSvg.addRectangle(100,length -20,4.5, width, "stroke-width:1px; stroke:#000000; fill: #ffffff");
    }

    private void addRafters(){
        for (double i = 100; i < width+100; i+= 55.714)
        {
            carportSvg.addRectangle(i, 20, length, 4.5,"stroke:#000000; fill: #ffffff" );
        }
    }




    @Override
    public String toString()
    {
        return carportSvg.toString();
    }
}
