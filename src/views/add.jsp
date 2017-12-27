<div style="text-align: center">
    <div style="text-align: center; text-decoration: underline; font-size: large">
        <h1>Add products to new transaction</h1>
    </div>
    <div style="float: right">
        <input style="background: none; border: none; text-decoration: underline" type="button" value="Log out" onclick="window.location='http://localhost:4567/logout'"><br><br>
    </div>
    <br>
    <br>
    <div style="font-size: large">
        <h2>Available products:</h2>
    </div>

    <div class="row row-3">
        #foreach($product in $products)
        <div class="col">
            <form name="frm" method="get" action="">
                <input name="hdnbt" type="hidden" placeholder="hdnbt"/>
                <input type="submit" name="bt" value="$product.getProductName()" onclick="{hdnbt.value = bt.value}" style="background: none; border: none; text-decoration: underline"  />
            </form>
        </div>
        #end
        <br>
        <br>
        <div class="button">
            <form name ="frm2" method="get" action="">
                <input name="goback" type="hidden">
                <input style="background-color: #EEEEFF" type="submit" name="return" value="Save and return" onclick="{goback.value='done'}"/>
                <input style="background-color: #EEEEFF" type="button" value="Return" onclick="window.location='http://localhost:4567/orders'"/>

            </form>
        </div>

    </div>

</div>
