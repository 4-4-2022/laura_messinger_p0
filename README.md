# laura_messinger_p0
💡 **project idea** 💡
- an online arcade / carnival where you earn redeemable "tickets" by playing games
- the games cost money to try, and the user has a "wallet" for their gaming budget
- users can add and remove funds from their wallet
- i will be tracking both the account's ticket balance and wallet balance
- accounts can have sub-users (for kids) that cannot modify the wallet balance
- the client interface will have a list of games they can try, with their prices
- "playing" the game just deducts from the wallet and returns a random outcome of tickets (lol)
- there will be a redemption store where the user can exchange their tickets for prizes
- naturally the user's personal information will include a mailing address for those^

🧠 **required technologies learned** 🧠
- [x] java 8
- [ ] maven
- [ ] junit
- [ ] mockito
- [ ] log4j / logback
- [ ] postgresql
- [ ] jdbc

🛠️ **format requirements followed** 🛠️
- [ ] build application in java 8
- [ ] manage project with maven
- [ ] get user input with scanner
- [ ] persist data with postgresql
- [ ] host data on aws relational database
- [ ] use jdbc data access objects to connect data
- [ ] write at least 30 junit tests for quality
- [ ] log all transactions with log4j or logback

🤸 **required functionality implemented** 🤸
- [ ] customers can register with a username and password
- [ ] customers can have multiple accounts with different credentials
- [ ] customers can add secondary users to their account (with limited access)
- [ ] customers can add and remove account funds, and transfer funds between accounts
- [ ] transactions are validated (no overdrawing, inputting negative amounts, etc)
- [ ] employees can view all customer information (users, balance, personal info)
- [ ] employees can cancel customer accounts
- [ ] administrator employees can edit customer balances and personal info
