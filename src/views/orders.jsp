<div style="text-align: center">
    <div style="text-align: center; text-decoration: underline; font-size: large">
        <h1>Your orders</h1>
    </div>

    <div style="float: right">
        <input style="background: none; border: none; text-decoration: underline" type="button" value="Log out" onclick="window.location='http://localhost:4567/logout'"><br><br>
    </div>

    #if($authenticationFailed)
    <h2>No user is logged in. Log first</h2>
    <input type="button" name="login" value="login" onclick="window.location='http://localhost:4567/login'">
    #else
    <div class="row row-3" style="border: inherit">
        #foreach($order in $orders)
        <div style="offset-before: 10%; text-decoration: underline">
            <h3>Order</h3>
        </div>
        <div class="col">
            <div class="transaction">
                #foreach ($product in $order)
                -
                $product.getProductName()
                <br>
                #end
            </div>
        </div>
        <br>
        <br>
        #end
    </div>

    <input type="button" style="background-color: #EEEEFF" name="add" value="Add new transaction" onclick="window.location='http://localhost:4567/add'">
    <input type="button" style="background-color: #EEEEFF" name="add" value="Return" onclick="window.location='http://localhost:4567/login'">


    #end
</div>
