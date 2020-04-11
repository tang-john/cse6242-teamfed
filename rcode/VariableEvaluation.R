library(tidyverse)
library(GGally)
library(rpart)
library(caret)
library(glmnet)

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
# test <- team_fed[ , -which(names(team_fed) %in% c("yearQtr", "year", "Qtr"))]

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

cor(master_NoNA$gdp, master_NoNA$medianHomePrice)

##################### Using LASSO Regression #################################

x <- as.matrix(master_NoNA[, -1])
y <- as.double(as.matrix(master_NoNA[, 1]))

x.scaled <- scale(x)
y.scaled <- scale(y)

fed_lasso <- cv.glmnet(x.scaled, y.scaled, standardize = F, alpha = 1,
                         family = "gaussian")

summary(fed_lasso)
fed_lasso$lambda.min

coef(fed_lasso$glmnet.fit, s = fed_lasso$lambda.min)

plot(fed_lasso, xvar = 'lambda')

############################ Time Series Evaluation ############################

library(tidyverse)
library(outliers)
library(forecast)
library(utils)

debt <- read_csv("C:/Users/Jason/Downloads/householdDebtTimeSeries.csv", col_names = F)

glimpse(debt)

debt_vec <- as.vector(debt)

debt_ts <- ts(debt_vec, start = 1999, frequency = 4)

plot(debt_ts)

smoothed <- HoltWinters(debt_ts, alpha = NULL, seasonal = "multiplicative")

smoothed$alpha
smoothed$beta
smoothed$gamma

plot(smoothed)
plot(smoothed$fitted)
plot(decompose(debt_ts))

acf(debt_ts)

forecast_1year <- forecast(smoothed, h = 4, findfrequency = TRUE)

plot(forecast_1year)