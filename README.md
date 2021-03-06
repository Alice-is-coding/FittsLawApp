# Fitt's Law Application.

#### 1. What is this law about ? 

<p>Fitt's law (1954) is a predictive model of human movement primarily used in HCI (Human-Computer Interaction) and ergonomics.</p>
<p>According to Fitt's law, human movement can be modeled by analogy to the transmission of information.</p> 
<p>Principle : The time taken to select something depends on the dimensions of the target and its distance.</p>
<p>Formula used here : T = a + b.Log2(1 + D/W) with : <br>
* T : The time taken to select the target. <br>
* D : The distance between the target and the device pointer. <br>
* W : Dimensions of the target. <br>
* a & b : Constants which depend on the pointing device used (should be obtained with measures, with a various number of different users and a test app).
</p>

#### 2. How does the app work ? 

<p>Actually, this is really simple, the HCI isn't really elaborate but it allows to make some experiment about Fitt's law. <br>
When launching the app (or calling the main method), a main interface appears where a first black circle is drawn.<br>
The goal is, with a pointing device of your choice, to select the circle as fast as you can. <br>
When you select a circle, it disappears and another is drawn. <br>
By default, the total number of circles drawn is 5. <br>
Finally, when the 5 circles have been selected and you close the app, the final results are logged in a "app.log" text file. <br>
By default, the pointing device logged into the file is "touchpad".
</p>
<p>If you would like to change the total number of circles drawn, please go to Main.class - line 124 and change controller.initExperiment(5); by whatever number you'd like. <br>
If you would like to change the type of pointing device used (to be logged in the file), please go to Main.class - line 108 and change resultFormatter = "\nPointing device : touchpad\n"; with whatever type of pointing device you use.
</p>
<br><br><br>
Caution : These experiments have a low scientific value. This experimental protocol is probably too naive, and the huge differences between values obtained make it 
delicate to build a linear model for T in function of ID. But it's a good exercise though.