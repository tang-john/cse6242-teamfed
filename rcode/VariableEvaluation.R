library(tidyverse)
library(GGally)
library(rpart)
library(caret)

team_fed <- read_csv("C:/Users/Jason/Downloads/masterdata.csv")

team_fed$Qtr <- as.factor(substr(team_fed$yearQtr, 6, 6))
team_fed$year <- as.integer(substr(team_fed$yearQtr, 0, 4))

team_fed <- team_fed[team_fed$year >= 1999,]

glimpse(team_fed)

ggplot(team_fed, aes(x = Qtr, y = householdDebt)) +
    geom_violin(aes(fill = Qtr), trim = FALSE, draw_quantiles = c(0.25, 0.5, 0.75)) #+ geom_boxplot(width = 0.1, lwd = 1)

bad_cols <- c("yearQtr", "autoLoan", "year", "Qtr", "effFundsRate",
              "autoDealerSales", "fed1YearYield", "fed1MonthYield", "householdDebtToGdp",
              "studentLoan")

test <- team_fed[, -which(names(team_fed) %in% bad_cols)]

test["MortgageDebt_Millions"] <- as.double(test["MortgageDebt_Millions"])

ggpairs(test)

write.csv(test, "C:/Users/Jason/Downloads/modelData.csv")

############## Look at variable importance using CARET ####################

control <- trainControl(method = "repeatedcv", number = 10, repeats = 3)

master_NoNA <- test[complete.cases(test),]

model <- train(householdDebt ~ ., data = master_NoNA, method = "rf", preProcess = "scale", importance = T, trControl = control, na.action = na.pass)

summary(model)

importance <- varImp(model, scale = F)

plot(importance)

cor(master_NoNA$studentLoan, master_NoNA$householdDebtToGdp)