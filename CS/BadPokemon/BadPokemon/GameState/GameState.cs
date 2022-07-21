
using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using Ope;
using Ope.objects;
using Ope.objects.Component;
using Ope.objects.System;

public abstract class GameState {
    protected GameStateManager gsm;
    protected SpriteBatch spriteBatch;

    protected Texture2D cursorSpr;

    protected RenderSystem rs;
    protected TextSystem ts;
    protected CursorSystem cs;
    protected ClickableSystem cls;
    protected trainerSystem trs;
    protected RectangleSystem res;
    protected TransformSystem tras;
    protected AttackSystem ats;
    protected HealthSystem hs;
    protected LevelSystem ls;

    protected Engine e1 = new Engine();
    protected Game1 game;

    public Entity creature1;

    public ContentManager Content
    {
        get;
        private set;
    }

    protected GameState(Game1 game, GameStateManager gsm){
        this.gsm = gsm;
        this.game = game;
        Content = new ContentManager(game.Services);
        Content.RootDirectory = "Content";
        init();
    }
    public int UILeftX = 96;
    public int UIRightX = 512;
    public int UIUpY = 256 + 120;
    public int UIDownY = 256 + 160;

    public int UICenter1X = 416;
    public int UICenter1Y = 288;
    public int UICenter2X = 32;
    public int UICenter2Y = 0;

    public Vector2[] creaturePos =
        {
            new Vector2(128, 250),
            new Vector2(672, 64)
        };
    public Texture2D loadTexture(string str)
    {
        return Content.Load<Texture2D>(str);
    }
    public Entity makeHealthBar(Vector2 pos)
    {
        Entity health = e1.CreateEntity();
        health.AddComponent(new TextureComponent());
        health.AddComponent(new TransformComponent());
        health.AddComponent(new RectangleComponent());
        health.AddComponent(new HealthComponent());
        health.GetComponent<TextureComponent>().texture = Content.Load<Texture2D>("health");
        health.GetComponent<TransformComponent>().position = pos;
        health.GetComponent<RectangleComponent>().Rect = new Rectangle(0, 0, 320, 16);
        return health;
    }
    public Entity makeText(Vector2 pos, SpriteFont font, string str, Color color)
    {
        Entity healthNum = e1.CreateEntity();
        healthNum.AddComponent(new TransformComponent());
        healthNum.AddComponent(new TextComponent());
        healthNum.GetComponent<TransformComponent>().position = pos;
        healthNum.GetComponent<TextComponent>().font = font;
        healthNum.GetComponent<TextComponent>().str = str;
        healthNum.GetComponent<TextComponent>().color = color;
        return healthNum;
    }
    public Entity makeCreature(Texture2D texture, string name, int type,int exp, int hp, int damage, int lvl)
    {
        SpriteFont font = Content.Load<SpriteFont>("font");
        Entity trekEnt = e1.CreateEntity();
        Vector2 pos;
        Vector2 posName;
        Vector2 posHealth;
        Vector2 poslvl;
        trekEnt.AddComponent(new TransformComponent());
        trekEnt.AddComponent(new TextureComponent());
        trekEnt.AddComponent(new RectangleComponent());
        trekEnt.AddComponent(new CreatureComponent());
        
        CreatureComponent creatureComp = trekEnt.GetComponent<CreatureComponent>();
        creatureComp.damage = damage;


        if (type == 0)
        {
            trekEnt.GetComponent<TextureComponent>().horizontalFLip = 1;
            posName = new Vector2(UICenter1X + 160, UICenter1Y);
            posHealth = new Vector2(UICenter1X + 96, UICenter1Y);
            poslvl = new Vector2(UICenter1X, UICenter1Y);
        }
        else
        {
            posName = new Vector2(UICenter2X + 160, UICenter2Y);
            posHealth = new Vector2(UICenter2X + 96, UICenter2Y);
            poslvl = new Vector2(UICenter2X, UICenter2Y);
        }
        pos = creaturePos[type];
        
        
        trekEnt.GetComponent<TextureComponent>().origin = new Vector2(64, 64);

        Entity trekName = trekEnt.GetComponent<CreatureComponent>().name = makeText(posName, font, name, Color.Black);
        Entity trekLvl = trekEnt.GetComponent<CreatureComponent>().lvl = e1.CreateEntity();
        trekLvl.AddComponent(new LevelComponent());
        trekLvl.AddComponent(new RectangleComponent());
        trekLvl.AddComponent(new TextureComponent());
        trekLvl.AddComponent(new TransformComponent());

        Entity trekLvlText = trekLvl.GetComponent<LevelComponent>().levelText = makeText(poslvl, font, "", Color.Black);

        trekLvl.GetComponent<RectangleComponent>().Rect = new Rectangle(0, 0, 320, 8);
        trekLvl.GetComponent<TransformComponent>().position = new Vector2(poslvl.X, poslvl.Y + 48);
        trekLvl.GetComponent<TextureComponent>().texture = loadTexture("expBar");
        trekLvl.GetComponent<LevelComponent>().level = lvl;
        trekLvl.GetComponent<LevelComponent>().exp = exp;
        trekEnt.GetComponent<TransformComponent>().position = pos;
        trekEnt.GetComponent<TextureComponent>().texture = texture;
        trekEnt.GetComponent<RectangleComponent>().Rect = new Rectangle(0, 0, 128, 128);
        creatureComp.type = type;
        creatureComp.healthBar = makeHealthBar(new Vector2(poslvl.X, poslvl.Y + 32));
        creatureComp.healthBar.GetComponent<HealthComponent>().hpStat = hp;
        creatureComp.healthBar.GetComponent<HealthComponent>().healthText = makeText(posHealth, font, creatureComp.healthBar.GetComponent<HealthComponent>().hpStat.ToString(), Color.Black);
        return trekEnt;
    }
    
    public Entity makeCreatureInvis()
    {
        Entity ent = e1.CreateEntity();
        ent.AddComponent(new StatShellComponent());
        ent.GetComponent<StatShellComponent>().exp = 0;
        ent.GetComponent<StatShellComponent>().hp = 100;
        ent.GetComponent<StatShellComponent>().creature = null;
        ent.GetComponent<StatShellComponent>().damage = 50;
        ent.GetComponent<StatShellComponent>().lvl = 1;

        return ent;
    }



    public void MakeCursor()
    {
        Entity cursorEnt = e1.CreateEntity();
        cursorEnt.AddComponent(new TransformComponent());
        cursorEnt.AddComponent(new CursorComponent());
        cursorEnt.AddComponent(new TextureComponent());
        cursorEnt.AddComponent(new RectangleComponent());
        cursorEnt.GetComponent<TextureComponent>().texture = cursorSpr;
    }

    public virtual void init()
    {
        // Create a new SpriteBatch, which can be used to draw textures.
        
        spriteBatch = new SpriteBatch(game.GraphicsDevice);
        rs = new RenderSystem(e1, spriteBatch);
        ts = new TextSystem(e1, spriteBatch);
        cs = new CursorSystem(e1, spriteBatch);
        cls = new ClickableSystem(e1);
        trs = new trainerSystem(e1, gsm);
        res = new RectangleSystem(e1);
        tras = new TransformSystem(e1);
        ats = new AttackSystem(e1, gsm);
        hs = new HealthSystem(e1);
        ls = new LevelSystem(e1);

        cursorSpr = Content.Load<Texture2D>("cursor");

        MakeCursor();
        creature1 = makeCreatureInvis();

    }
    public abstract void EnterBattle();
    
    public virtual void update(GameTime gameTime)
    {
        handleInput();
        cs.update(gameTime);
        cls.update(gameTime);
        trs.update(gameTime);
        res.update(gameTime);
        tras.update(gameTime);
        ats.update(gameTime);
        hs.update(gameTime);
        ls.update(gameTime);
    }
    public virtual void draw()
    {
        rs.draw();
        ts.draw();
        cs.draw();
    }
    public abstract void handleInput();
}