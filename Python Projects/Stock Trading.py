
import numpy as np
import alpaca_trade_api as tradeapi
import time


SEC_KEY = '79QLkxVlJFJJxnH5QiroSTCE3iT2gihH9X4pRT4T' # Enter Your Secret Key Here
PUB_KEY = 'AKI45K0IENS32RKMZIYA' # Enter Your Public Key Here
BASE_URL = 'https://paper-api.alpaca.markets' # This is the base URL for paper trading
api = tradeapi.REST(key_id= PUB_KEY, secret_key=SEC_KEY, base_url=BASE_URL) # For real trading, don't enter a base_url


symb = "SPY"
pos_held = False

while True:
    print("")
    print("Checking Price")
    
    market_data = api.get_barset(symb, 'minute', limit=5) # Get one bar object for each of the past 5 minutes

    close_list = [] # This array will store all the closing prices from the last 5 minutes
    for bar in market_data[symb]:
        close_list.append(bar.c) # bar.c is the closing price of that bar's time interval

    close_list = np.array(close_list, dtype=np.float64) # Convert to numpy array
    moving_average = np.mean(close_list)
    last_price = close_list[4] # Most recent closing price

    print("Moving Average: " + str(moving_average))
    print("Last Price: " + str(last_price))

    
    if moving_average + 0.1 < last_price and not pos_held: # If MA is more than 10cents under price, and we haven't already bought
        print("Buy")
        api.submit_order(
            symbol=symb,
            qty=1,
            side='buy',
            type='market',
            time_in_force='gtc'
        )
        pos_held = True
    elif moving_average - 0.1 > last_price and pos_held: # If MA is more than 10cents above price, and we already bought
        print("Sell")
        api.submit_order(
            symbol=symb,
            qty=1,
            side='sell',
            type='market',
            time_in_force='gtc'
        )
        pos_held = False
    
    time.sleep(60)